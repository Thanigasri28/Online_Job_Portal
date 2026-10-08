import java.awt.*;
import java.util.*;
import javax.swing.*;

class Job {
    String title, company, location, skillRequired;
    double salary;

    Job(String title, String company, String location, double salary, String skill) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.salary = salary;
        this.skillRequired = skill;
    }

    @Override
    public String toString() {
        return title + " | " + company + " | " + location +
                " | Skill: " + skillRequired + " | ₹" + salary;
    }
}

class JobSeeker {
    String name, email, skill, resume;

    JobSeeker(String n, String e, String s, String r) {
        name = n;
        email = e;
        skill = s;
        resume = r;
    }

    @Override
    public String toString() {
        return name + " (" + email + ") - Skill: " + skill;
    }
}

class Employer {
    String companyName, username;

    Employer(String c, String u) {
        companyName = c;
        username = u;
    }

    @Override
    public String toString() {
        return companyName + " (" + username + ")";
    }
}

public class OnlineJobPortal extends JFrame {

    private final java.util.List<JobSeeker> seekers = new ArrayList<>();
    private final java.util.List<Employer> employers = new ArrayList<>();
    private final java.util.List<Job> jobs = new ArrayList<>();
    private final Map<Job, java.util.List<JobSeeker>> applications = new HashMap<>();

    public OnlineJobPortal() {

        setTitle("Colorful Job Portal System");
        setSize(550, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1, 10, 10));
        mainPanel.setBackground(new Color(230, 245, 255));

        JLabel heading = new JLabel("ONLINE JOB PORTAL", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(new Color(0, 60, 150));

        JButton regSeek = customButton("Register Job Seeker");
        JButton regEmp = customButton("Register Employer");
        JButton loginSeek = customButton("Job Seeker Login");
        JButton loginEmp = customButton("Employer Login");
        JButton exit = customButton("Exit");

        mainPanel.add(heading);
        mainPanel.add(regSeek);
        mainPanel.add(regEmp);
        mainPanel.add(loginSeek);
        mainPanel.add(loginEmp);
        mainPanel.add(exit);

        add(mainPanel);

        regSeek.addActionListener(e -> registerSeeker());
        regEmp.addActionListener(e -> registerEmployer());
        loginSeek.addActionListener(e -> seekerLogin());
        loginEmp.addActionListener(e -> employerLogin());
        exit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private JButton customButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setBackground(new Color(0, 120, 200));
        b.setForeground(Color.white);
        b.setFocusPainted(false);
        return b;
    }

    // ---------------- REGISTER JOB SEEKER ---------------------
    private void registerSeeker() {

        JFrame f = new JFrame("Register Job Seeker");
        f.setSize(400, 450);
        f.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBackground(new Color(255, 240, 230));

        JLabel title = new JLabel("JOB SEEKER REGISTRATION", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField skill = new JTextField();
        JTextArea resume = new JTextArea(4, 20);

        JScrollPane scroll = new JScrollPane(resume);

        JButton submit = customButton("Register");

        panel.add(new JLabel("Name:"));
        panel.add(name);
        panel.add(new JLabel("Email:"));
        panel.add(email);
        panel.add(new JLabel("Skill:"));
        panel.add(skill);
        panel.add(new JLabel("Resume:"));
        panel.add(scroll);
        panel.add(new JLabel());
        panel.add(submit);

        f.setLayout(new BorderLayout());
        f.add(title, BorderLayout.NORTH);
        f.add(panel, BorderLayout.CENTER);

        submit.addActionListener(e -> {
            seekers.add(new JobSeeker(name.getText(), email.getText(),
                    skill.getText(), resume.getText()));
            JOptionPane.showMessageDialog(f, "Job Seeker Registered!");
            f.dispose();
        });

        f.setVisible(true);
    }

    // ---------------- REGISTER EMPLOYER ---------------------
    private void registerEmployer() {
        JFrame f = new JFrame("Register Employer");
        f.setSize(400, 300);
        f.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(new Color(240, 255, 240));

        JLabel title = new JLabel("EMPLOYER REGISTRATION", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JTextField comp = new JTextField();
        JTextField user = new JTextField();
        JButton submit = customButton("Register");

        panel.add(new JLabel("Company Name:"));
        panel.add(comp);
        panel.add(new JLabel("Username:"));
        panel.add(user);
        panel.add(new JLabel());
        panel.add(submit);

        f.setLayout(new BorderLayout());
        f.add(title, BorderLayout.NORTH);
        f.add(panel, BorderLayout.CENTER);

        submit.addActionListener(e -> {
            employers.add(new Employer(comp.getText(), user.getText()));
            JOptionPane.showMessageDialog(f, "Employer Registered!");
            f.dispose();
        });

        f.setVisible(true);
    }

    // ---------------- JOB SEEKER LOGIN ---------------------
    private void seekerLogin() {
        if (seekers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No job seekers registered!");
            return;
        }

        JobSeeker selected = (JobSeeker) JOptionPane.showInputDialog(
                this,
                "Select Job Seeker:",
                "Login",
                JOptionPane.PLAIN_MESSAGE,
                null,
                seekers.toArray(),
                seekers.get(0));

        if (selected != null)
            openSeekerPanel(selected);
    }

    // ---------------- EMPLOYER LOGIN ---------------------
    private void employerLogin() {
        if (employers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employers registered!");
            return;
        }

        Employer selected = (Employer) JOptionPane.showInputDialog(
                this,
                "Select Employer:",
                "Login",
                JOptionPane.PLAIN_MESSAGE,
                null,
                employers.toArray(),
                employers.get(0));

        if (selected != null)
            openEmployerPanel(selected);
    }

    // ---------------- EMPLOYER PANEL ---------------------
    private void openEmployerPanel(Employer emp) {

        JFrame f = new JFrame("Employer Panel - " + emp.companyName);
        f.setSize(500, 450);
        f.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(new Color(240, 255, 240));

        JLabel title = new JLabel("POST NEW JOB", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JTextField t = new JTextField();
        JTextField loc = new JTextField();
        JTextField sal = new JTextField();
        JTextField skill = new JTextField();

        JButton post = customButton("Post Job");
        JButton viewApps = customButton("View Applications");

        panel.add(new JLabel("Job Title:"));
        panel.add(t);
        panel.add(new JLabel("Location:"));
        panel.add(loc);
        panel.add(new JLabel("Salary:"));
        panel.add(sal);
        panel.add(new JLabel("Skill Required:"));
        panel.add(skill);
        panel.add(post);
        panel.add(viewApps);

        f.setLayout(new BorderLayout());
        f.add(title, BorderLayout.NORTH);
        f.add(panel, BorderLayout.CENTER);

        post.addActionListener(e -> {
            try {
                Job j = new Job(t.getText(), emp.companyName, loc.getText(),
                        Double.parseDouble(sal.getText()), skill.getText());
                jobs.add(j);
                applications.put(j, new ArrayList<>());
                JOptionPane.showMessageDialog(f, "Job Posted Successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Enter valid salary!");
            }
        });

        viewApps.addActionListener(e -> viewApplications());

        f.setVisible(true);
    }

    // ---------------- VIEW APPLICATIONS ---------------------
    private void viewApplications() {
        JFrame f = new JFrame("Applications");
        f.setSize(500, 450);
        f.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 250, 230));

        JLabel heading = new JLabel("JOB APPLICATIONS", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));

        DefaultListModel<String> model = new DefaultListModel<>();
        Map<Integer, Job> jobIndex = new HashMap<>();
        Map<Integer, JobSeeker> seekIndex = new HashMap<>();

        int i = 0;

        for (Job j : applications.keySet()) {
            for (JobSeeker s : applications.get(j)) {
                model.addElement("Job: " + j.title + " | Applicant: " + s.name);
                jobIndex.put(i, j);
                seekIndex.put(i, s);
                i++;
            }
        }

        JList<String> list = new JList<>(model);
        JButton accept = customButton("Accept Candidate");

        accept.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx != -1) {
                Job job = jobIndex.get(idx);
                JobSeeker seeker = seekIndex.get(idx);

                JOptionPane.showMessageDialog(
                        f,
                        "Candidate " + seeker.name + " ACCEPTED for: " + job.title
                );
            }
        });

        panel.add(heading, BorderLayout.NORTH);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(accept, BorderLayout.SOUTH);

        f.add(panel);
        f.setVisible(true);
    }

    // ---------------- JOB SEEKER PANEL ---------------------
    private void openSeekerPanel(JobSeeker seeker) {

        JFrame f = new JFrame("Job Seeker - " + seeker.name);
        f.setSize(550, 450);
        f.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 255));

        JLabel heading = new JLabel("JOBS MATCHING: " + seeker.skill, JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));

        DefaultListModel<String> model = new DefaultListModel<>();
        Map<Integer, Job> map = new HashMap<>();

        int i = 0;
        for (Job j : jobs) {
            if (j.skillRequired.equalsIgnoreCase(seeker.skill)) {
                model.addElement(j.toString());
                map.put(i, j);
                i++;
            }
        }

        JList<String> list = new JList<>(model);
        JButton apply = customButton("Apply");

        apply.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx != -1) {
                Job job = map.get(idx);
                applications.get(job).add(seeker);
                JOptionPane.showMessageDialog(f, "Applied Successfully!");
            }
        });

        panel.add(heading, BorderLayout.NORTH);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(apply, BorderLayout.SOUTH);

        f.add(panel);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        new OnlineJobPortal();
    }
}

