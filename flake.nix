{
  description = "Deterministic LaTeX compilation with Nix";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixpkgs-unstable";
    flake-utils.url = "github:numtide/flake-utils";
    git-hooks.url = "github:cachix/git-hooks.nix";
  };

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
              nixfmt-rfc-style.enable = true;
              statix.enable = true;
              chktex.enable = false;
              latexindent = {
                enable = true;
                settings = {
                  flags = "--local --silent --modifylinebreak --overwriteIfDifferent";
                };
              };
              lacheck.enable = false;
              actionlint.enable = true;
              prettier = {
                enable = true;
                settings = {
                  prose-wrap = "always";
                };
              };
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
