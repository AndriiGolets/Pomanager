import {PomPropertyValue} from "./pom-property-value";
import {PomProperty} from "./pom-property";
import {PomPackage} from "./pom-package";
import {KeyValue} from "@angular/common";

export class PomTable {

   pomTableMap: Map<string, Map<string, PomPropertyValue>> = new Map<string, Map<string, PomPropertyValue>>();
   pomPackageNameMap: Map<string, PomPackage> = new Map<string, PomPackage>();
   pomPropertyNameMap: Map<string, PomProperty> = new Map<string, PomProperty>();

  private onCompare(_left: KeyValue<any, any>, _right: KeyValue<any, any>): number {
    return -1;
  }

}
