import {Collaborator} from "./Collaborator";
import {EventLike} from "./EventLike";
import {Office} from "./Office";
import {NoteE} from "./NoteE";
import {User} from "./User";

export class Event {
   idEvent!: number;
   name!: string;
   description!: string;
   startDate!: string;
   endDate!: string;
   lat!: string;
   lng!: string;
   attendsNumber!: number;
   tags!: string;
   eventType!: string;
   eventNote!: string;
   likes!: number;
   users!: User;
   collaborators!: Collaborator;
   notes!: NoteE;
   likeEvent!: EventLike;
   offices!: Office;
  file!: Image;
}


export interface Image{
  idFile: number;
  name: string;
  type: string;
  ImageUrl: string;
}
