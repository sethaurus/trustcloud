Rationale
=========

== Connection

- A CA has been created and a server cert issued.
- The client has the CA cert to veryify the server with.
- There is no client verification

== Protocol Basics

- All communications are intiated by the client.
- The first byte of communication is the size of the message.
- The second byte is the command type.

== Uploads

- Client provides filename & raw data.

== Vouching

- Client requests to vouch for a file using a cert.
- Server responds with SHA-256 of file as a challenge.
- Client responds by signing this chanllenge with private key.
- Server verifies this agains the ceritificate.

== Server Data Storage

- We assume that the server FS is never tampered with.
- Files are stored in data/files
- Certificates are stores in data/certs
- Vouches are stored as files named after the voucher in data/vouch/<files|certs>/<name of item>