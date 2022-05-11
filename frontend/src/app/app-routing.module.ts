import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {SigninComponent} from "./signin/signin.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {EventComponent} from "./event/event.component";
import {ShowEventComponent} from "./show-event/show-event.component";
import {ForumContainerComponent} from "./forum/forum-container/forum-container.component";
import {ForumPostComponent} from "./forum/forum-post/forum-post.component";
import {CalendarComponent} from "./calendar/calendar.component";

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'Login', component: LoginComponent},
  {path: 'singin', component: SigninComponent},
  {path: 'Event', component: EventComponent},
  {path: "ShowEvent", component: ShowEventComponent},
  {path: "calendar", component: CalendarComponent},
  {path: '', redirectTo: 'Login', pathMatch: 'full'},
  {path: 'forum', component: ForumContainerComponent},
  {path: 'forum/view/**', component: ForumPostComponent},
  {path: "**", component: NotFoundComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
