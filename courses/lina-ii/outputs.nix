{
  flake-utils,
  self,
  ...
}:
flake-utils.lib.eachDefaultSystem (
  system:
  let
    inherit (self.lib.${system}) mkLaTeXProblemSets;
  in
  {
    packages = flake-utils.lib.flattenTree {
      lina-ii =
        mkLaTeXProblemSets {
          srcDir = ./.;
          outputFilePattern = "Gruppe26_Pia-Girschick_Michael-van-Straten_Aufgabe%d.%d.pdf";
        }
        // {
          recurseForDerivations = true;
        };
    };
  }
)
