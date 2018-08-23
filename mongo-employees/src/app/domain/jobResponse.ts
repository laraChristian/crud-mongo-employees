import { Job } from "../entities/job";

export class JobResponse {

    success: boolean;
    message: String;
    jobs: Array<Job>;
}