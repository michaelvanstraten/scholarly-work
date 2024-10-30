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
      lina-i =
        mkLaTeXProblemSets {
          srcDir = ./.;
          outputFilePattern = "Jan-Frischgesell_Michael-van-Straten_%d-%d.pdf";
        }
        // {
          recurseForDerivations = true;
        };
    };
  }
)
