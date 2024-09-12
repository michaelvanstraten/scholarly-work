{
  description = "Deterministic LaTeX compilation with Nix";

  inputs.flake-utils.url = "github:numtide/flake-utils";
  inputs.git-hooks.url = "github:michaelvanstraten/git-hooks.nix";

  outputs =
    {
      self,
      nixpkgs,
      flake-utils,
      git-hooks,
    }:
    { }
    // flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = import nixpkgs { inherit system; };

        texlive = pkgs.texliveFull;
      in
      {
        lib = import ./lib { inherit (pkgs) lib newScope; };

        packages =
          with pkgs.lib;
          let
            inherit (fileset) fileFilter toList;
            inherit (lists) forEach;
            problemset-files = toList (fileFilter (file: file.hasExt "tex") ./.);
            problemsets = forEach problemset-files (
              problemset-file:
              let
                problemset-name = strings.removePrefix "./" (path.removePrefix ./. problemset-file);
              in
              {
                ${problemset-name} = self.lib.${system}.mkLaTeXDerivation {
                  inherit problemset-file problemset-name;
                };
              }
            );
          in
          attrsets.mergeAttrsList problemsets;

        checks = {
          git-hooks = git-hooks.lib.${system}.run {
            src = ./.;
            hooks = {
              # nix checks
              nixfmt = {
                enable = true;
                package = pkgs.nixfmt-rfc-style;
              };
              statix.enable = true;
              # LaTeX checks
              chktex.enable = false;
              latexindent.enable = true;
              lacheck.enable = false;
              # Other checks
              actionlint.enable = true;
              # markdownlint.enable = true;
              prettier.enable = true;
              trim-trailing-whitespace.enable = true;
            };
          };
        };

        formatter = pkgs.nixfmt-rfc-style;

        devShells.default = pkgs.mkShell {
          inherit (self.checks.${system}.git-hooks) shellHook;
          buildInputs =
            [
              self.checks.${system}.git-hooks.enabledPackages
              (texlive.withPackages (
                packages: with packages; [
                  latexdiff
                  latexpand
                  git-latexdiff
                ]
              ))
            ]
            ++ (with pkgs; [
              texlab
              pdftk
            ]);
        };
      }
    );
}
