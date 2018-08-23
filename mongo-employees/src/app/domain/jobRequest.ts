import { Job } from "../entities/job";

export class JobRequest {

    update: boolean;
    job: Job;

    constructor() {
        this.update = false;
        this.job = new Job();
    }
}