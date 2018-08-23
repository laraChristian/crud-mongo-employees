import { Commons } from "../ui-utilities/commons";
import { JobRequest } from "../../domain/jobRequest";
import { Message } from 'primeng/components/common/api';
import { Job } from "../../entities/job";

export class JobsModel extends Commons {

    request: JobRequest;
    msgs: Message[];
    cols: any[];
    jobsDtbl: Array<Job>;
    ccRegex: RegExp = /^[a-zA-Z\s]+$/;
    disableDelete: boolean;


    constructor() {
        super();
        this.clean();
        this.initColumns();
        this.msgs = [];
    }

    clean() {
        this.request = new JobRequest();
        this.disableDelete = true;
    }

    initColumns(): void {
        this.jobsDtbl = new Array<Job>();
        this.cols = [{ field: 'jobTittle', header: 'Job' }, { field: 'minSalary', header: 'Min-Salary' },
        { field: 'maxSalary', header: 'Max-Salary' },]
    }

    mapRowDataToRequest(rowData: Job) {
        this.request.job.id = rowData.id;
        this.request.job.jobTittle = rowData.jobTittle;
        this.request.job.maxSalary = rowData.maxSalary;
        this.request.job.minSalary = rowData.minSalary;
    }
}