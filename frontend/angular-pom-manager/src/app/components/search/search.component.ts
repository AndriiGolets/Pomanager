import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Filter} from "../../common/filter";
import {debounceTime, takeUntil} from "rxjs";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchComponent implements OnInit {

  @Input()
  set filter(filter: Filter) {
    console.log(filter);
    this.formGroup.setValue(filter, {emitEvent: false});
  }

  @Output()
  filterUpdate: EventEmitter<Filter> = new EventEmitter();

  formGroup: FormGroup = new FormGroup({
    packageFilter: new FormControl()
  });

  constructor() {
  }

  ngOnInit(): void {
    this.formGroup
      .get('packageFilter')
      .valueChanges.pipe(debounceTime(350))
      .subscribe((value) => {
        console.log(value);
        this.filterUpdate.emit(<Filter>{
          ...this.formGroup.value,
          packageFilter: value,
        });
      });
  }

  onSubmit() {
    console.log(this.formGroup.value);
  }

}
