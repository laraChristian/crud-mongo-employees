import { Employee } from "../entities/employee";

export class EmployeeResponse {

    success: boolean;
    message: String;
    employees: Array<Employee>;
}