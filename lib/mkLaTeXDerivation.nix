{
  pkgs,
  lib,
  stdenvNoCC,
}:
{
  problemset-name,
  problemset-file,
  texlive ? pkgs.texliveFull,
}:
stdenvNoCC.mkDerivation {
  src =
    with lib;
    fileset.toSource {
      root = ../.;
      fileset = fileset.unions [
        problemset-file
        ../problemset.cls
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

    SOURCE_DATE_EPOCH="" latexmk \
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
}
