export class PomPackage {

  name: string
  version: string
  gitBranch: string
  branchStatus: number

  constructor(name: string, version: string, gitBranch: string, branchStatus: number) {
    this.name = name;
    this.version = version;
    this.gitBranch = gitBranch;
    this.branchStatus = branchStatus;
  }
}
