import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PomTableComponent } from './pom-table.component';

describe('PomTableComponent', () => {
  let component: PomTableComponent;
  let fixture: ComponentFixture<PomTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PomTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PomTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
