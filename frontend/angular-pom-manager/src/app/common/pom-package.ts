export class PomPackage {

  name: string
  version: string
  gitBranch: string

  constructor(name: string, version: string, gitBranch: string) {
    this.name = name;
    this.version = version;
    this.gitBranch = gitBranch;
  }
}
