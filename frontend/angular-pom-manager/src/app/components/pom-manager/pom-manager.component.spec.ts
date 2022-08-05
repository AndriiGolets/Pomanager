import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PomManagerComponent } from './pom-manager.component';

describe('PomManagerComponent', () => {
  let component: PomManagerComponent;
  let fixture: ComponentFixture<PomManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PomManagerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PomManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
