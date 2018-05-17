package cn.huahai.showProject.bean;

import java.io.Serializable;


public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8754418513043302746L;
	private String Uname;
	private String password;
	public String getUname() {
		return Uname;
	}
	public void setUname(String Uname) {
		this.Uname = Uname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((Uname == null) ? 0 : Uname.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (Uname == null) {
			if (other.Uname != null)
				return false;
		} else if (!Uname.equals(other.Uname))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [Uname=" + Uname + ", password=" + password + "]";
	}
	public User(String Uname, String password) {
		super();
		this.Uname = Uname;
		this.password = password;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

}
