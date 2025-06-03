import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatefamilypasswordComponent } from './updatefamilypassword.component';

describe('UpdatefamilypasswordComponent', () => {
  let component: UpdatefamilypasswordComponent;
  let fixture: ComponentFixture<UpdatefamilypasswordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdatefamilypasswordComponent]
    });
    fixture = TestBed.createComponent(UpdatefamilypasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
