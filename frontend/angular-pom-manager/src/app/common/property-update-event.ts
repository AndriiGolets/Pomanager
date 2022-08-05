export class PropertyUpdateEvent {

  packageName: string;
  propertyName: string;
  newValue: string;

  constructor(packageName: string, propertyName: string, newValue: string) {
    this.packageName = packageName;
    this.propertyName = propertyName;
    this.newValue = newValue;
  }
}
