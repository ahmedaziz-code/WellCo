import { NgModule } from '@angular/core';
import { FullCalendarModule } from '@fullcalendar/angular'; // must go before plugins
import dayGridPlugin from '@fullcalendar/daygrid'; // a plugin!
import interactionPlugin from '@fullcalendar/interaction'; // a plugin!
import { BrowserModule } from '@angular/platform-browser';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SigninComponent } from './signin/signin.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { EventComponent } from './event/event.component';
import {FormsModule} from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {HttpClientModule} from "@angular/common/http";
import {MatDialogModule} from "@angular/material/dialog";
import { RegisterComponent } from './register/register.component';
import { ShowEventComponent } from './show-event/show-event.component';
import { ForumContainerComponent } from './forum/forum-container/forum-container.component';
import { ForumCardComponent } from './forum/forum-card/forum-card.component';
import { ForumPostComponent } from './forum/forum-post/forum-post.component';
import { ForumPostModalComponent } from './forum/forum-post-modal/forum-post-modal.component';
import {AgmCoreModule} from '@agm/core';
import { CalendarComponent } from './calendar/calendar.component';
import { EventDetailsComponent } from './event-details/event-details.component';


FullCalendarModule.registerPlugins([
  dayGridPlugin,
  interactionPlugin
]);

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    SigninComponent,
    NotFoundComponent,
    EventComponent,
    RegisterComponent,
    ShowEventComponent,
    ForumContainerComponent,
    ForumCardComponent,
    ForumPostComponent,
    //FullCalendarModule,
    ForumPostModalComponent,
    CalendarComponent,
    EventDetailsComponent
  ],
  imports: [
    BrowserModule,
    FullCalendarModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatDialogModule,
    ToastrModule.forRoot(),
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyCT9yFcjMhtbC8eItK-iuO3aWDxMN5ZARM',
      libraries: ['places']
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
