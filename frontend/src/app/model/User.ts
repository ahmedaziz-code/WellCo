import {Role} from "./Enums/Role";

export class User{
  id !: number;
  login !: string;
  email !: string;
  password !: string;
  role !: Role;
}

