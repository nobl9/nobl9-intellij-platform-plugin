{ pkgs ? import <nixpkgs> { } }:
let
  myPkgs = with pkgs; [
    fontconfig
    e2fsprogs
    xorg.libX11
    xorg.libXext
    xorg.libXrender
    xorg.libXtst
    xorg.libXi
  ];
in
pkgs.mkShell {
  buildInputs = myPkgs;
  shellHook = ''
    # Automatically create an LD_LIBRARY_PATH for each package.
    export LD_LIBRARY_PATH="${pkgs.lib.makeLibraryPath myPkgs}:$LD_LIBRARY_PATH"
    echo "LD_LIBRARY_PATH set to: $LD_LIBRARY_PATH"
  '';
}
