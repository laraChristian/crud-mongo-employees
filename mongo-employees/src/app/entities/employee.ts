import { Email } from "./email";
import { Department } from "./department";
import { Job } from "./job";

export class Employee {

    id: any;
    firstName: String;
    lastName: String;
    email: Email;
    phoneNumber: String;
    hireDate: Date;
    identification: String;
    manager: Employee;
    department: Department;
    job: Job;

}