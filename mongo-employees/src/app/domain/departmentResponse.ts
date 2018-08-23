import { Department } from "../entities/department";

export class DepartmentResponse {

    public success: boolean;
    public message: String;
    public departments: Array<Department>;

}