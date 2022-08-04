import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Filter} from "../../common/filter";
import {Router} from "@angular/router";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchComponent implements OnInit {

  @Input()
  set filter(filter: Filter) {
    this.searchFormGroup.setValue(filter, {emitEvent: false});
  }

  @Output()
  filterUpdate: EventEmitter<Filter> = new EventEmitter();

  searchFormGroup: FormGroup = new FormGroup({
    propertyFilter: new FormControl(),
    packageFilter: new FormControl()
  });

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    const currentFilterKeys = Object.keys(this.searchFormGroup.value).filter(k => this.searchFormGroup.value[k]);

    const newFilter = Object.fromEntries(currentFilterKeys.map((k) => [k, this.searchFormGroup.value[k]]));

    this.router.navigate([], {queryParams: {...newFilter}});
  }

}
