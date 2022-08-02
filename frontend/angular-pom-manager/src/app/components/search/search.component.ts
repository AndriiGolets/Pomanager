import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Filter} from "../../common/filter";
import {filter} from "rxjs/operators";
import {ActivatedRoute, Router, RoutesRecognized} from "@angular/router";
import {LocationStrategy} from "@angular/common";

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
    packageFilter: new FormControl()
  });

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private location: LocationStrategy) {
    router.events
      .pipe(filter(e => e instanceof RoutesRecognized))
      .subscribe(e => {
        const current = this.activatedRoute.queryParams.subscribe(params => {
          const current = params['packageFilter'];
          if (current) {
            this.filterUpdate.emit(this.createNewFilter(current));
          } else {
            this.filterUpdate.emit(this.createNewFilter(""));
          }
        });
      });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    const filter = this.formGroup
      .get('packageFilter').value;

    this.router.navigate(['search'], {queryParams: {packageFilter: filter}})

    this.filterUpdate.emit(this.createNewFilter(filter));
  }

  private createNewFilter(packageFilter: string): Filter {
    return <Filter>{
      ...this.formGroup.value,
      packageFilter: packageFilter,
    };
  }

}
