{
  lib,
  mkLaTeXProblemSet,
  ...
}:
{
  srcDir,
  outputFilePattern ? "Aufgabe %d.%d.pdf",
}:
let
  isTexFile = file: file.hasExt "tex";
  mkAttrName =
    path:
    lib.path.removePrefix srcDir path
    |> lib.strings.removePrefix "./"
    |> lib.strings.removeSuffix ".tex";
in
srcDir
|> lib.fileset.fileFilter isTexFile
|> lib.fileset.toList
|> builtins.map (pset: {
  ${mkAttrName pset} = mkLaTeXProblemSet {
    inherit pset outputFilePattern;
  };
})
|> lib.mergeAttrsList
