{
  nixpkgs,
  flake-utils,
  latix,
  ...
}:
flake-utils.lib.eachDefaultSystem (
  system:
  let
    inherit (latix.lib.${system}) buildLatexmkProject;
    pkgs = import nixpkgs { inherit system; };
  in
  {
    packages = {
      "internships/spring-break-2024" = buildLatexmkProject {
        name = "internships/spring-break-2024";
        buildInputs = [ pkgs.texliveSmall ];
        src = ./spring-break-2024.tex;
        filename = ./spring-break-2024.tex;
      };
    };
  }
)
