import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficBarComponent } from './grafic-bar.component';

describe('GraficBarComponent', () => {
  let component: GraficBarComponent;
  let fixture: ComponentFixture<GraficBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GraficBarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GraficBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
