import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForumContainerComponent } from './forum-container.component';

describe('ForumContainerComponent', () => {
  let component: ForumContainerComponent;
  let fixture: ComponentFixture<ForumContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ForumContainerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ForumContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
