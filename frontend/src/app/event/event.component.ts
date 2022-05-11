import { Component, OnInit } from '@angular/core';
import {Event} from "../model/Event";
import {EventService} from "../services/event.service";
import {Office} from "../model/Office";
import {NgForm} from "@angular/forms";
import {EventType} from "../model/Enums/eventType";
import {MatDialog} from "@angular/material/dialog";
import { ToastrService } from 'ngx-toastr';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  eventType = EventType;
  keys = [];
  event : Event = new Event();
  listEvent !: Event[];
  listOffice !: Office[];
  office : Office = new Office();
  imageURL : any;
  file : any;
  userFile : any;
  selected !: number;
  DateTime = new Date();
  verifff !: boolean;
  lat = 37.214314;
  lng = 10.127558;
  lat1:any;
  lng1:any;

  update(e:any ){
    this.selected = e.target.value
  }




  constructor(private service : EventService, private dialogRef: MatDialog, private toastr: ToastrService) {
    // @ts-ignore
    this.keys = Object.keys(this.eventType);
  }

  ngOnInit(): void {
    this.refresh();
    this.refreshOffice();
  }


  clearForm(){
    (<HTMLFormElement>document.getElementById("saveEvent")).reset();
  }

  refresh(){
    this.service.getEvents().subscribe((data: Event[])=>{this.listEvent= data; console.log(data)})
  }
  refreshOffice(){
    this.service.getOffice().subscribe((data:Office[])=>{this.listOffice=data});
  }

  saveEvent(){
    console.log(this.event);
    this.event.lat = this.lat1;
    this.event.lng = this.lng1;
    var eventData = JSON.stringify(this.event);
    console.log(this.verifEndDate(this.event.startDate, this.event.endDate));
    console.log(this.verifStartDate(this.event.startDate));
    if(this.verifEndDate(this.event.startDate, this.event.endDate)){
      this.service.addEvent(eventData,this.selected, this.file ).subscribe(res=>{console.log(res);
        this.toastr.success("event Added Succesfully"); this.clearForm();this.refresh()});
    }
    else{
      this.toastr.error("Verifier Vos Données !");
    }
  }

  deleteEvent(idEvent: number){
    console.log(idEvent);
    this.service.deleteEvent(idEvent).subscribe(res=>{this.toastr.success("Event Deleted Succesfully"); this.refresh()});
  }

  addOffice(){
    console.log(this.office.officeName)
    this.service.addOffice(this.office).subscribe(res=>{this.refreshOffice(); this.toastr.success("Office Added succesfully")});

  }

  onSelectedImage(e: any){
    this.userFile = e.target.files[0];
    // @ts-ignore
    this.file = document.querySelector('input[type=file]').files[0];
    var reader = new FileReader();
    reader.readAsDataURL(this.file);
    reader.onload = (res=>{this.imageURL = reader.result})
    console.log(this.userFile);
  }

  getDay(date:string){
    let startDate = new Date(date);
    return startDate.getDate();
  }

  verifStartDate(date : string){
    let startDate = new Date(date);
    if (this.DateTime > startDate){
      return false;
    }
    return true;
  }

  verifEndDate(startDate:string, endDate:string){
    let end = new Date(endDate);
    let start = new Date(startDate);
    if(this.DateTime > end){
      return false;
    }
    else if(start > end){
      return false;
    }
    return true;
  }

  selectMarker(lat:number, lng:number){
    this.lat1 = lat;
    this.lng1 = lng;
  }

}
