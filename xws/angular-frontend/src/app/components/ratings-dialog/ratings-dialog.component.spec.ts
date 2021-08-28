import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RatingsDialogComponent } from './ratings-dialog.component';

describe('RatingsDialogComponent', () => {
  let component: RatingsDialogComponent;
  let fixture: ComponentFixture<RatingsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RatingsDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RatingsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
