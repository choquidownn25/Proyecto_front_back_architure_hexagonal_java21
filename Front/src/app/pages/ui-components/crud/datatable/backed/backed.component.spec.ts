import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BackedComponent } from './backed.component';

describe('BackedComponent', () => {
  let component: BackedComponent;
  let fixture: ComponentFixture<BackedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BackedComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BackedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
