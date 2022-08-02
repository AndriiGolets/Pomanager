import {PomPropertyValue} from "./pom-property-value";
import {PomProperty} from "./pom-property";
import {PomPackage} from "./pom-package";

export class PomTable {

  pomTableMap: Map<string, Map<string, PomPropertyValue>> = new Map<string, Map<string, PomPropertyValue>>();
  pomPackageNameMap: Map<string, PomPackage> = new Map<string, PomPackage>();
  pomPropertyNameMap: Map<string, PomProperty> = new Map<string, PomProperty>();

  constructor(json?: PomTableJson) {
    if (json) {
      Object.keys(json.pomTableMap).forEach(ptmKey => {
        let ptmVal = json.pomTableMap[ptmKey];
        let ppvMap = new Map<string, PomPropertyValue>();
        Object.keys(ptmVal).forEach(ppvKey => {
          let ppvVal = ptmVal[ppvKey];
          let ppv = new PomPropertyValue(ppvVal.propertyName);
          ppvMap.set(ppvKey, ppv);
        })
        this.pomTableMap.set(ptmKey, ppvMap);
      })

      Object.keys(json.pomPropertyNameMap).forEach(ppnKey => {
        let ppnVal = json.pomPropertyNameMap[ppnKey];
        this.pomPropertyNameMap.set(ppnKey, new PomProperty(ppnVal.name))
      })

      Object.keys(json.pomPackageNameMap).forEach(ppknKey => {
        let ppknVal = json.pomPackageNameMap[ppknKey];
        this.pomPackageNameMap.set(ppknKey, new PomPackage(ppknVal.name, ppknVal.version, ppknVal.gitBranch))
      })
    }
  }

  public filterByProperty(propertyName: string) {
    this.pomPropertyNameMap.forEach((val, key, map) => {
      if (!key.includes(propertyName)) {
        map.delete(key);
      }
    });
    this.removeEmptyPackages(propertyName);
  }

  public filterByPackage(packageName: string) {
    this.pomPackageNameMap.forEach((val, key) => {
      if (!key.includes(packageName)) {
        this.pomTableMap.delete(key);
      }
    });
    this.removeEmptyProperties();
  }

  private removeEmptyPackages(propertyName: string) {
    this.pomTableMap.forEach((val, key, map) => {
      if (Array.from(val.keys()).filter(key => key.includes(propertyName)).length == 0) {
        map.delete(key);
      }
    })
  }

  private removeEmptyProperties() {
    let properties = []
    this.pomTableMap.forEach((val, key) => {
      properties = properties.concat(Array.from(val.keys()));
    });
    const propertiesToDelete = Array.from(this.pomPropertyNameMap.keys()).filter(p => !properties.includes(p));
    propertiesToDelete.forEach(val => {
      this.pomPropertyNameMap.delete(val);
    });
  }

  public copy(): PomTable {
    const newPomTable = new PomTable();
    newPomTable.pomTableMap = new Map(this.pomTableMap);
    newPomTable.pomPackageNameMap = new Map(this.pomPackageNameMap);
    newPomTable.pomPropertyNameMap = new Map(this.pomPropertyNameMap);
    return newPomTable;
  }

}

export interface PomTableJson {
  pomTableMap: object;
  pomPackageNameMap: object;
  pomPropertyNameMap: object;
}
