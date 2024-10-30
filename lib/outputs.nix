{
  flake-utils,
  latix,
  nixpkgs,
  ...
}@inputs:
let
  inherit (nixpkgs.lib.attrsets) recursiveUpdate;
in
recursiveUpdate
  {
    lib.callOutputs = import ./callOutputs.nix inputs;
  }
  (
    flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = import nixpkgs { inherit system; };
      in
      {
        lib = pkgs.lib.makeScope pkgs.newScope (
          scope:
          let
            inherit (scope) callPackage;
          in
          {
            mkLaTeXProblemSet = callPackage ./mkLaTeXProblemSet.nix {
              inherit (latix.lib.${system}) buildLatexmkProject;
            };
            mkLaTeXProblemSets = callPackage ./mkLaTeXProblemSets.nix { };
          }
        );
      }
    )
  )
