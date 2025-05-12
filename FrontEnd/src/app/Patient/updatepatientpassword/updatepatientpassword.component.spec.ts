import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatepatientpasswordComponent } from './updatepatientpassword.component';

describe('UpdatepatientpasswordComponent', () => {
  let component: UpdatepatientpasswordComponent;
  let fixture: ComponentFixture<UpdatepatientpasswordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdatepatientpasswordComponent]
    });
    fixture = TestBed.createComponent(UpdatepatientpasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
