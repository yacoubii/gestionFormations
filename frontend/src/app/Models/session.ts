export interface Session{
  id?:number
  lieu:string
  dateDeb :string
  dateFin:string
  nbrParticipants:number
  organisme?:any
  formateur?:any
  formation?:any
}
