{
  description = "Learning Spark and Scala at the same time.";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
    sbt-derivation.url = "github:zaninime/sbt-derivation";
    sbt-derivation.inputs.nixpkgs.follows = "nixpkgs";
    flake-parts.url = "github:hercules-ci/flake-parts";
  };

  outputs = { self, nixpkgs, flake-parts, sbt-derivation }@inputs:
    flake-parts.lib.mkFlake { inherit inputs; } {
      systems = [ "x86_64-linux" ];

      perSystem = { config, pkgs, system, ... }: {
        packages = {
          # default = sbt-derivation.lib.mkSbtDerivation {
          #   inherit pkgs;
          #   pname = "learningSpark";
          #   version = "unstable";

          #   src = ./.;

          #   depsSha256 = "sha256-guP9qZs7OoZtlWSYeBzMBJ6rWcarJ4tAylYSlW8vxn8=";
          # };
        };
        devShells = {
          default = pkgs.mkShell {
            buildInputs = with pkgs; [ sbt ];
            shellHook = "export PS1='\\e[1;34mdev > \\e[0m'";
          };
        };
      };
    };
}
