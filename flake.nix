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
    {
      lib.latexmk = import ./build-document.nix;
    }
    // flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = import nixpkgs { inherit system; };

        texlive = pkgs.texliveFull;
      in
      {
        packages =
          with pkgs.lib;
          let
            inherit (fileset)
              fileFilter
              toList
              unions
              toSource
              ;
            inherit (lists) forEach;
            problemset-files = toList (fileFilter (file: file.hasExt "tex") ./.);
            problemsets = forEach problemset-files (
              problemset-file:
              let
                problemset-name = strings.removePrefix "./" (path.removePrefix ./. problemset-file);
              in
              {
                ${problemset-name} = pkgs.stdenvNoCC.mkDerivation {
                  src = toSource {
                    root = ./.;
                    fileset = unions [
                      problemset-file
                      ./problemset.cls
                    ];
                  };
                  name = problemset-name;

                  buildInputs =
                    (with pkgs; [
                      coreutils
                      ncurses
                    ])
                    ++ [ texlive ];

                  TEXMFHOME = "./cache";
                  TEXMFVAR = "./cache/var";

                  OSFONTDIR = "/share/fonts";

                  buildPhase = ''
                    runHook preBuild

                    SOURCE_DATE_EPOCH="${toString self.lastModified}" latexmk \
                    -interaction=nonstopmode \
                    -pdf \
                    -lualatex \
                    -pretex='\pdfvariable suppressoptionalinfo 512\\relax' \
                    -usepretex \
                    "${problemset-name}"

                    runHook postBuild
                  '';

                  installPhase = ''
                    runHook preInstall

                    install -d $out && install -m644 -D *.pdf $out/

                    runHook postInstall
                  '';
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
