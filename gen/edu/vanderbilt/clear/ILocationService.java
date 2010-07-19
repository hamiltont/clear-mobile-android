/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\Projects\\Android\\CLEAR-client-android2\\src\\edu\\vanderbilt\\clear\\ILocationService.aidl
 */
package edu.vanderbilt.clear;
public interface ILocationService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements edu.vanderbilt.clear.ILocationService
{
private static final java.lang.String DESCRIPTOR = "edu.vanderbilt.clear.ILocationService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an edu.vanderbilt.clear.ILocationService interface,
 * generating a proxy if needed.
 */
public static edu.vanderbilt.clear.ILocationService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof edu.vanderbilt.clear.ILocationService))) {
return ((edu.vanderbilt.clear.ILocationService)iin);
}
return new edu.vanderbilt.clear.ILocationService.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getLat:
{
data.enforceInterface(DESCRIPTOR);
double _result = this.getLat();
reply.writeNoException();
reply.writeDouble(_result);
return true;
}
case TRANSACTION_getLon:
{
data.enforceInterface(DESCRIPTOR);
double _result = this.getLon();
reply.writeNoException();
reply.writeDouble(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements edu.vanderbilt.clear.ILocationService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public double getLat() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
double _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLat, _data, _reply, 0);
_reply.readException();
_result = _reply.readDouble();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public double getLon() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
double _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLon, _data, _reply, 0);
_reply.readException();
_result = _reply.readDouble();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getLat = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getLon = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public double getLat() throws android.os.RemoteException;
public double getLon() throws android.os.RemoteException;
}
