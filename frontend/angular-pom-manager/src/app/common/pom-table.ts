import {PomPropertyValue} from "./pom-property-value";
import {PomProperty} from "./pom-property";
import {PomPackage} from "./pom-package";
import {KeyValue} from "@angular/common";


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
        this.pomPackageNameMap.set(ppknKey, new PomPackage(ppknVal.name))
      })
    }
  }

  private onCompare(_left: KeyValue<any, any>, _right: KeyValue<any, any>): number {
    return -1;
  }
}

export interface PomTableJson {
  pomTableMap: object;
  pomPackageNameMap: object;
  pomPropertyNameMap: object;
}
