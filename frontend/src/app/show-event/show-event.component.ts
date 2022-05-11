import { Component, OnInit, AfterViewInit } from '@angular/core';
import {Event} from "../model/Event";
import {EventService} from "../services/event.service";
import tt from '@tomtom-international/web-sdk-maps';
import {environment} from "../../environments/environment";
import {Observable, Subscriber } from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Location} from "../model/location";
import SearchBox from '@tomtom-international/web-sdk-plugin-searchbox';
import { services } from '@tomtom-international/web-sdk-services';
import { CalendarOptions } from '@fullcalendar/angular';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { User } from '../model/User';
import { CommentEvent } from '../model/commentEvent';
import { EventNote } from '../model/Enums/eventNote';


@Component({
  selector: 'app-show-event',
  templateUrl: './show-event.component.html',
  styleUrls: ['./show-event.component.css']
})
export class ShowEventComponent implements OnInit{

  calendarOptions: CalendarOptions = {
    initialView: 'dayGridMonth'
  };

  cmt!: string;
  listComment !: CommentEvent[];
  ttsearchbox = new SearchBox(services);
  map : any;
  location !: Location[];
  marker : any;
  eventList !: Event[];
  event : Event = new Event();
  idEvent : any;
  host = 'http://localhost:8089/WellCo'
  lat = 37.214314;
  lng = 10.127558;
  lat1:any;
  lng1:any;
  nbrLike = 0;
  user : User = new User();
  listEvent !: Event[];
  likeDislike =0;
  comments = [];
  nbrCmnt=0;
  note!: EventNote ;
  private position: any;
  constructor(private service: EventService, private httpclient: HttpClient,public dialog: MatDialog, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.service.getEvents().subscribe(res=>{this.eventList=res});
    this.user.id = 2;

  }

  // public ngAfterViewInit(): void {
  //   this.loadMap();
  // }
  //
  // private getCurrentPosition(): any {
  //   return new Observable((observer: Subscriber<any>) => {
  //     if (navigator.geolocation) {
  //       navigator.geolocation.getCurrentPosition((position: any) => {
  //         observer.next({
  //           latitude: position.coords.latitude,
  //           longitude: position.coords.longitude,
  //         });
  //         observer.complete();
  //       });
  //     } else {
  //       observer.error();
  //     }
  //   });
  // }
  //
  // private loadMap(): void {
  //   this.map = tt.map({
  //     key: environment.tomtom.key,
  //     container: 'map',
  //   });
  //   var options = {
  //     searchOptions: {
  //       key: environment.tomtom.key,
  //       language: 'en-GB',
  //       limit: 5
  //     },
  //     autocompleteOptions: {
  //       key: environment.tomtom.key,
  //       language: 'en-GB'
  //     }
  //   };
  //
  //   var serchBox = new SearchBox(services, options);
  //   this.map.addControl(serchBox, 'top-left');
  //   this.map.addControl(new tt.FullscreenControl());
  //   this.map.addControl(new tt.NavigationControl());
  //
  //   this.getCurrentPosition()
  //     .subscribe((position: any) => {
  //       this.map.flyTo({
  //         center: {
  //           lat: Number(this.event.lat),
  //           lng: Number(this.event.lng),
  //         },
  //         zoom: 13,
  //       });
  //
  //       const popup = new tt.Popup({ anchor: 'bottom', offset: { bottom: [0, -40] } }).setHTML('Angular TomTom');
  //
  //       var marker = new tt.Marker().setLngLat({
  //         lat: Number(this.event.lat),
  //         lng: Number(this.event.lng),
  //       }).addTo(this.map);
  //       marker.setPopup(popup).togglePopup();
  //     });
  // }

  public eventDetails(e : Event){
    this.event = e;
    this.idEvent = e.idEvent;
    this.lat1 = Number(this.event.lat);
    this.lng1 = Number(this.event.lng);;
    this.service.getCommentByEvent(this.event.idEvent).subscribe(res=>{this.listComment=res; console.log(res);this.nbrCmnt=res.length});
    console.log(this.nbrCmnt);
  }

  selectMarker(lat:number, lng:number){
    this.lat1 = lat;
    this.lng1 = lng;
  }



  Toast(){
    this.toastr.success("You had succefully participate to the event check the Calendar");
  }

  like(){
    if(this.likeDislike == 0){

      this.likeDislike=1;
      this.service.likeEvent(this.event.idEvent,this.user.id).subscribe(res=>{this.refresh(); console.log(res)});
      console.log(this.event.likes);
    }
    else{

      this.likeDislike=0;
      this.service.removeLike(this.event.idEvent,this.user.id).subscribe(res=>{this.refresh()});
    }
  }

  participate(e : Event){
    this.service.joinEvent(e.idEvent,this.user.id).subscribe(rest=>{this.refreshEvent();this.toastr.success("You had succefully participate to the event check the Calendar")});
  }

  comment(){
    this.service.commentEvent(this.event.idEvent, this.user.id, this.cmt).subscribe(res=>{this.refreshComments(); this.resetContent()});
    console.log(this.listComment);
  }

  deleteComment(id : number){
    this.service.uncommentEvent(id).subscribe(res=>{this.refreshComments()});
  }

  selectEvent(e : Event){
    this.event = e;
  }

  noteEvent2(e : Event){
    this.note = EventNote.note2;
    this.service.noteEvent(e.idEvent,this.user.id,this.note).subscribe(res=>{this.refreshEvent();this.toastr.success("Voted : Not Bad")})
  }
  noteEvent3(e : Event){
    this.note = EventNote.note3;
    this.service.noteEvent(e.idEvent,this.user.id,this.note).subscribe(res=>{this.refreshEvent();this.toastr.success("Voted : Good")})
  }
  noteEvent4(e : Event){
    this.note = EventNote.note4;
    this.service.noteEvent(e.idEvent,this.user.id,this.note).subscribe(res=>{this.refreshEvent();this.toastr.success("Voted : Verry Good")})
  }
  noteEvent5(e : Event){
    this.note = EventNote.note5;
    this.service.noteEvent(e.idEvent,this.user.id,this.note).subscribe(res=>{this.refreshEvent();this.toastr.success("Voted : Excelent")})
  }
  noteEvent1(e : Event){
    this.note = EventNote.note1;
    this.service.noteEvent(e.idEvent,this.user.id,this.note).subscribe(res=>{this.refreshEvent();this.toastr.success("Voted : Verry Bad")})
  }

  refreshEvent(){
    this.service.getEvents().subscribe(res=>{this.eventList=res});
  }
  refresh(){
    this.service.getEvent(this.event.idEvent).subscribe((data: Event)=>{this.event= data; console.log(data)})
  }
  refreshComments(){
    this.service.getCommentByEvent(this.event.idEvent).subscribe(res=>{this.listComment=res; this.nbrCmnt=res.length});
  }
  resetContent(){
    (<HTMLFormElement>document.getElementById('content')).reset();
  }




}
