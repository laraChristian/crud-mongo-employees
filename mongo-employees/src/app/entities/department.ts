import { Employee } from "./employee";
import { Locations } from "./locations";

export class Department {

    id: any;
    name: String;
    manager: Employee;
    location: Locations;
}