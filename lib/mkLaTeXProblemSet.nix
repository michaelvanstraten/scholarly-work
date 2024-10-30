{
  pkgs,
  lib,
  buildLatexmkProject,
}:
{
  pset,
  texlive ? pkgs.texlive.combine {
    inherit (pkgs.texlive)
      scheme-basic
      # Additional packages
      babel-german
      enumitem
      etoolbox
      isodate
      mathtools
      substr
      thmtools
      xpatch
      zref
      ;
  },
  outputFilePattern,
  ...
}@args:
let
  jobName = builtins.baseNameOf pset |> lib.strings.removeSuffix ".tex";

  cleanedArgs = removeAttrs args [
    "outputFilePattern"
    "pSet"
    "texlive"
  ];

  buildOutput =
    buildLatexmkProject {
      buildInputs = [ texlive ];
      filename = lib.path.removePrefix ../. pset;
      name = jobName;
      src =
        with lib.fileset;
        toSource {
          root = ../.;
          fileset = unions [
            pset
            ../problemset.cls
          ];
        };
    }
    // cleanedArgs;

  psetmeta = "${buildOutput}/${jobName}.psetmeta";
in
pkgs.runCommand "split-${jobName}.sh" { buildInputs = [ pkgs.qpdf ]; } # bash
  ''
    # Ensure the split info file exists
    if [ ! -e "${psetmeta}" ]; then
      echo "Error: Split info file '${psetmeta}' not found."
      exit 1
    fi

    read -r problemSet < "${psetmeta}"

    mkdir $out

    # Read the split info file and process each problem
    tail -n +2 "${psetmeta}" | while read -r problemNumber startPage endPage; do
      outputFile=$(printf "$out/${outputFilePattern}" "$problemSet" "$problemNumber")

      qpdf "${buildOutput}/${jobName}.pdf" --pages . $startPage-$endPage -- "$outputFile"

      echo "Created: $outputFile"
    done
  ''
