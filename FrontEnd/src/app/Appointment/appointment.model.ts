export interface Appointment {

  id : number;
  familyname : string;
  doctorname : string;
  date : Date,
  nature : number,
  period : number,
  tariff : number,
  emergency : number,
  quoted : boolean,
  description : string,
  skipped : boolean
}
