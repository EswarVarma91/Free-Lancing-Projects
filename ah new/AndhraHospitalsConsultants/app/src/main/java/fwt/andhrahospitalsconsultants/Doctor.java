package fwt.andhrahospitalsconsultants;

class Doctor {
    String patient_id;
    String department;
    String appointmentdate;
    String timed;
    String named;
    String username;
    String usermobile;
    String problem;
    String age;
    String mobile;



    public Doctor(String patient_id, String department, String appointmentdate, String time, String problem, String age, String mobile, String name) {
        this.department = department;
        this.appointmentdate = appointmentdate;
        this.timed = time;
        this.problem = problem;
        this.age = age;
        this.patient_id = patient_id;
        this.mobile = mobile;
        this.named=name;
    }

    public String getName() {
        return named;
    }

    public void setName(String name) {
        this.named = name;
    }
    public String getPatient_id() {
        return patient_id;
    }

    public void patient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAppointmentdate() {
        return appointmentdate;
    }

    public void setAppointmentdate(String appointmentdate) {
        this.appointmentdate = appointmentdate;
    }

    public String getTime() {
        return timed;
    }

    public void setTime(String time) {
        timed = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
