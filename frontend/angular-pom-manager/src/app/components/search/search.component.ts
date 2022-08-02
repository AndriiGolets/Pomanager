import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Filter} from "../../common/filter";
import {ActivatedRoute, Router, RoutesRecognized} from "@angular/router";
import {LocationStrategy} from "@angular/common";
import { filter } from 'rxjs';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchComponent implements OnInit {

  @Input()
  set filter(filter: Filter) {
    this.formGroup.setValue(filter, {emitEvent: false});
  }

  @Output()
  filterUpdate: EventEmitter<Filter> = new EventEmitter();

  formGroup: FormGroup = new FormGroup({
    propertyFilter: new FormControl(),
    packageFilter: new FormControl()
  });

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private location: LocationStrategy) {
    router.events
      .pipe(filter(e => e instanceof RoutesRecognized))
      .subscribe(e => {
        this.activatedRoute.queryParams.subscribe(params => {
          const current = params['packageFilter'];
          if (current) {
            this.filterUpdate.emit(this.createNewFilter(current, ""));
          } else {
            this.filterUpdate.emit(this.createNewFilter("", ""));
          }
        });
      });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    const propertyFilter = this.formGroup
      .get('propertyFilter').value;
    const packageFilter = this.formGroup
      .get('packageFilter').value;

    this.router.navigate(['search'], {queryParams: {packageFilter: propertyFilter}})

    this.filterUpdate.emit(this.createNewFilter(propertyFilter, packageFilter));
  }

  private createNewFilter(propertyFilter: string, packageFilter: string): Filter {
    return <Filter>{
      propertyFilter: propertyFilter,
      packageFilter: packageFilter
    };
  }

}
