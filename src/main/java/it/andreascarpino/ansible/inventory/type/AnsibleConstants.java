/*
 * The MIT License (MIT)
 * Copyright (c) 2016 Andrea Scarpino <me@andreascarpino.it>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.andreascarpino.ansible.inventory.type;

/**
 * @author Andrea Scarpino
 */
public class AnsibleConstants {

	/**
	 * Connection type to the host. Candidates are <code>local</code>,
	 * <code>smart</code>, <code>ssh</code>, <code>paramiko</code> or
	 * <code>winrm</code>. The default is <code>smart</code>.
	 *
	 * @see AnsibleConnection
	 */
	public static final String ANSIBLE_CONNECTION = "ansible_connection";

	/**
	 * The name of the host to connect to, if different from the alias you wish
	 * to give to it.
	 */
	public static final String ANSIBLE_HOST = "ansible_host";

	/**
	 * Ansible 2.0 has deprecated the "ssh" keyword.
	 *
	 * @deprecated
	 *
	 */
	@Deprecated
	public static final String ANSIBLE_SSH_HOST = "ansible_ssh_host";

	/**
	 * The ssh port number, if not 22.
	 */
	public static final String ANSIBLE_PORT = "ansible_port";

	/**
	 * Ansible 2.0 has deprecated the "ssh" keyword.
	 *
	 * @deprecated
	 *
	 */
	@Deprecated
	public static final String ANSIBLE_SSH_PORT = "ansible_ssh_port";

	/**
	 * The default ssh user name to use.
	 */
	public static final String ANSIBLE_USER = "ansible_user";

	/**
	 * Ansible 2.0 has deprecated the "ssh" keyword.
	 *
	 * @deprecated
	 *
	 */
	@Deprecated
	public static final String ANSIBLE_SSH_USER = "ansible_ssh_user";

	/**
	 * The user password to use (this is insecure, we strongly recommend using
	 * <code>--ask-pass</code> or SSH keys).
	 */
	public static final String ANSIBLE_PASSWORD = "ansible_password";

	/**
	 * Ansible 2.0 has deprecated the "ssh" keyword.
	 *
	 * @deprecated
	 *
	 */
	@Deprecated
	public static final String ANSIBLE_SSH_PASS = "ansible_ssh_pass";

	/**
	 * Private key file used by ssh. Useful if using multiple keys and you don't
	 * want to use SSH agent.
	 */
	public static final String ANSIBLE_SSH_PRIVATE_KEY_FILE = "ansible_ssh_private_key_file";

	/**
	 * This setting is always appended to the default command line for sftp,
	 * scp, and ssh. Useful to configure a <code>ProxyCommand</code> for a
	 * certain host (or group).
	 */
	public static final String ANSIBLE_SSH_COMMON_ARGS = "ansible_ssh_common_args";

	/**
	 * This setting is always appended to the default sftp command line.
	 */
	public static final String ANSIBLE_SFTP_EXTRA_ARGS = "ansible_sftp_extra_args";

	/**
	 * This setting is always appended to the default scp command line.
	 */
	public static final String ANSIBLE_SCP_EXTRA_ARGS = "ansible_scp_extra_args";

	/**
	 * This setting is always appended to the default ssh command line.
	 */
	public static final String ANSIBLE_SSH_EXTRA_ARGS = "ansible_ssh_extra_args";

	/**
	 * Determines whether or not to use SSH pipelining. This can override the
	 * <code>pipelining</code> setting in <code>ansible.cfg</code>.
	 */
	public static final String ANSIBLE_SSH_PIPELINING = "ansible_ssh_pipelining";

	/**
	 * Equivalent to <code>ansible_sudo</code> or <code>ansible_su</code>,
	 * allows to force privilege escalation.
	 */
	public static final String ANSIBLE_BECOME = "ansible_become";

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final String ANSIBLE_SUDO = "ansible_sudo";

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final String ANSIBLE_SU = "ansible_su";

	/**
	 * Allows to set privilege escalation method.
	 */
	public static final String ANSIBLE_BECOME_METHOD = "ansible_become_method";

	/**
	 * Equivalent to <code>ansible_sudo_user</code> or
	 * <code>ansible_su_user</code>, allows to set the user you become through
	 * privilege escalation.
	 */
	public static final String ANSIBLE_BECOME_USER = "ansible_become_user";

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final String ANSIBLE_SUDO_USER = "ansible_sudo_user";

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final String ANSIBLE_SU_USER = "ansible_su_user";

	/**
	 * Equivalent to <code>ansible_sudo_pass</code> or
	 * <code>ansible_su_pass</code>, allows you to set the privilege escalation
	 * password.
	 */
	public static final String ANSIBLE_BECOME_PASS = "ansible_become_pass";

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final String ANSIBLE_SUDO_PASS = "ansible_sudo_pass";

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final String ANSIBLE_SU_PASS = "ansible_su_pass";

	/**
	 * The shell type of the target system. Commands are formatted using
	 * 'sh'-style syntax by default. Setting this to <code>csh</code> or
	 * <code>fish</code> will cause commands executed on target systems to
	 * follow those shell's syntax instead.
	 */
	public static final String ANSIBLE_SHELL_TYPE = "ansible_shell_type";

	/**
	 * The target host python path. This is useful for systems with more than
	 * one Python or not located at <code>/usr/bin/python</code> such as BSD, or
	 * where <code>/usr/bin/python</code> is not a 2.X series Python. We do not
	 * use the <code>/usr/bin/env</code> mechanism as that requires the remote
	 * user's path to be set right and also assumes the <code>python</code>
	 * executable is named python, where the executable might be named something
	 * like <code>python26</code>.
	 */
	public static final String ANSIBLE_PYTHON_INTERPRETER = "ansible_python_interpreter";

	/**
	 * Specify the connection scheme (<code>http</code> or <code>https</code>)
	 * to use for the WinRM connection. Ansible uses <code>https</code> by
	 * default unless the port is 5985.
	 *
	 * @see AnsibleWinRMScheme
	 */
	public static final String ANSIBLE_WINRM_SCHEME = "ansible_winrm_scheme";

	/**
	 * Specify an alternate path to the WinRM endpoint. Ansible uses
	 * <code>/wsman</code> by default.
	 */
	public static final String ANSIBLE_WINRM_PATH = "ansible_winrm_path";

	/**
	 * Specify the realm to use for Kerberos authentication. If the username
	 * contains "@", Ansible will use the part of the username after "@" by
	 * default.
	 */
	public static final String ANSIBLE_WINRM_REALM = "ansible_winrm_realm";

	/**
	 * Specify one or more transports as a comma-separated list. By default,
	 * Ansible will use <code>kerberos,plaintext</code> if the "kerberos" module
	 * is installed and a realm is defined, otherwise <code>plaintext</code>.
	 */
	public static final String ANSIBLE_WINRM_TRANSPORT = "ansible_winrm_transport";

	/**
	 * Specify the server certificate validation mode (<code>ignore</code> or
	 * <code>validate</code>). Ansible defaults to <code>validate</code> on
	 * Python 2.7.9 and higher, which will result in certificate validation
	 * errors against the Windows self-signed certificates. Unless verifiable
	 * certificates have been configured on the WinRM listeners, this should be
	 * set to <code>ignore</code>.
	 */
	public static final String ANSIBLE_WINRM_SERVER_CERT_VALIDATION = "ansible_winrm_server_cert_validation";

	private AnsibleConstants() {
	}

	/**
	 * @author Andrea Scarpino
	 */
	public class AnsibleConnection {

		public static final String LOCAL = "local";

		public static final String SMART = "smart";

		public static final String SSH = "ssh";

		public static final String PARAMIKO = "paramiko";

		public static final String WINRM = "winrm";

		private AnsibleConnection() {
		}

	}

	/**
	 * @author Andrea Scarpino
	 */
	public class AnsibleWinRMScheme {

		public static final String HTTP = "http";

		public static final String HTTPS = "https";

		private AnsibleWinRMScheme() {
		}

	}

	/**
	 * @author Andrea Scarpino
	 */
	public class AnsibleWinRMServerCertValidation {

		public static final String IGNORE = "ignore";

		public static final String VALIDATE = "validate";

		private AnsibleWinRMServerCertValidation() {
		}

	}

}
