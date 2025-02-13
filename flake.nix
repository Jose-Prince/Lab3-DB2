{
  description = "A very basic flake";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
  };

  outputs = {
    self,
    nixpkgs,
  }: {
    packages.x86_64-linux.default = let
      pkgs = import nixpkgs {system = "x86_64-linux";};
    in
      pkgs.mkShell {
        packages = [pkgs.jdk pkgs.maven];
        shellHook = ''
          alias run="mvn exec:java -Dexec.mainClass='com.mycompany.app.App'"
        '';
      };
  };
}
