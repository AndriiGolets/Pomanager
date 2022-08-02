export class Filter {

  propertyFilter: string
  packageFilter: string

  constructor(propertyFilter: string, packageFilter: string) {
    this.propertyFilter = propertyFilter;
    this.packageFilter = packageFilter;
  }
}
