import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForumPostModalComponent } from './forum-post-modal.component';

describe('ForumPostModalComponent', () => {
  let component: ForumPostModalComponent;
  let fixture: ComponentFixture<ForumPostModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ForumPostModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ForumPostModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
