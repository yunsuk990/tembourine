package com.example.tem.home

import android.app.Activity
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.tem.R
import com.example.tem.databinding.FragmentRegisterBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class RegisterFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentRegisterBinding
    private val cycle= mutableListOf<String>()
    private var title:String?=null
    var var_img:Bitmap?=null

    var customArgument = 0;
    var startCalendar: Calendar? = null
    var endCalendar: Calendar? = null
    var spinnerPos: Int? = null

    lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var resultLauncher_g: ActivityResultLauncher<Intent>
    companion object{
        private const val ARG_TITLE = "title"
        lateinit var prefs: ItemFile

        fun newInstance(title: String): RegisterFragment {
            val fragment = RegisterFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }
    private fun registerFragment(fragment: Fragment, title: String) {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .commit()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        prefs = ItemFile(requireContext().applicationContext)
        resultLauncher=
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if(it.resultCode== AppCompatActivity.RESULT_OK){
                    Log.d("원하는 거",prefs.getImgAddress(""))
                    if(prefs.getImgAddress("").isNotBlank()){
                        val file= File(prefs.getImgAddress(""))
                        var bitmap: Bitmap?=null
                        if(Build.VERSION.SDK_INT<28){
                            bitmap= MediaStore.Images.Media
                                .getBitmap(requireContext().contentResolver, Uri.fromFile(file))
                            binding.regImg.setImageBitmap(bitmap)
                            var_img=bitmap
                        }
                        else{
                            val decode= ImageDecoder.createSource(this.requireContext().contentResolver,
                                Uri.fromFile(file.absoluteFile))
                            bitmap= ImageDecoder.decodeBitmap(decode)
                            binding.regImg.setImageBitmap(bitmap)
                            var_img=bitmap
                        }
                        if(bitmap!=null){
                            saveImageFile(file.name,getExtension(file.name),bitmap)
                        }
                    }
                }
            }
        resultLauncher_g=
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if(it.resultCode== AppCompatActivity.RESULT_OK){
                    it.data?.data?.let { uri ->
                        val imageUri:Uri?=it.data?.data
                        if(imageUri!=null){
                            Glide.with(requireContext().applicationContext).load(imageUri).override(500,500)
                                .into(binding.regImg)
                            var_img=getBitmapFromView(binding.regImg)
                            /*Handler(Looper.getMainLooper()).postDelayed({var_img=getBitmapFromView(binding.callImg)
                                                                        Log.d("img","$var_img")
                                                                        binding.callImg.setImageDrawable(null)},2000)
                            Handler(Looper.getMainLooper()).postDelayed({binding.callImg.setImageBitmap(var_img)},4000)*/
                        }
                    }
                }
            }
        arguments?.let {
            title=it.getString(ARG_TITLE)
            Log.d("위치",title.toString())
        }
        binding.regImg.setOnClickListener {
            //requestGallary()
            requestPermission()
        }

        if(title!=null){
            binding.placeItem.text=title
        }

        // 알람 주기 설정
        val spinner:Spinner=binding.spinReg
        val items= arrayOf("7일","14일","30일","60일")
        val adapter = CustomSpinnerAdapter(requireContext(), items)
        adapter.setMyItemClickListener(object: CustomSpinnerAdapter.MyItemClickListener{
            override fun itemClick(position: Int) {
                spinnerPos = position
            }
        })
        spinner.adapter = adapter

        //뒤로 가기
        binding.backBtn.setOnClickListener {
            registerFragment(HomeFragment(),"1")
        }

        //시작 날짜 설정
        binding.itemBuy.setOnClickListener {
            customArgument = 1
            val dialogFragment = DatePickerFragment.newInstance(1)
            dialogFragment.show(childFragmentManager, "datePicker")
        }

        //마감 날짜 설정
        binding.itemDate.setOnClickListener {
            customArgument = 2
            val dialogFragment = DatePickerFragment.newInstance(2)
            dialogFragment.show(childFragmentManager, "datePicker")
        }

        // 반복알림 스위치에 따른 반복주기 보여주기
        binding.swithArm.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
               if (p1){
                   binding.settingNotifyCl.visibility = View.VISIBLE
               }else{
                   binding.settingNotifyCl.visibility = View.INVISIBLE
               }
            }
        })

        binding.btnReg.setOnClickListener {
            if(binding.itemName.text.isEmpty()){
                Toast.makeText(requireContext(), "상품명을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if(startCalendar == null ){
                Toast.makeText(requireContext(), "구입한 날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }else if(endCalendar == null){
                Toast.makeText(requireContext(), "사용 기한 날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }else{
                // 반복주기 설정됐을시
                if(binding.swithArm.isChecked){
                    var num = items[spinnerPos!!].split("일")
                    Log.d("spinnerPos", num[0])
                    startAlarm(endCalendar!!, binding.itemName.text.toString(), num[0].toInt())
                }else{
                    startAlarm(endCalendar!!, binding.itemName.text.toString(), 0)
                }
                registerFragment(HomeFragment(),"1")
            }

        }

        return view
    }
    private fun requestGallary() {
        val permission= object : PermissionListener {
            override fun onPermissionGranted() {
                //Toast.makeText(this@PluscarActivity,"갤러리 허용",Toast.LENGTH_SHORT).show()
                move_gallery()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(requireContext(), "갤러리 권한이 거부 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        TedPermission.create()
            .setPermissionListener(permission)
            .setDeniedMessage("갤러리 권한을 허용해 주세요")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }

    private fun move_gallery() {
        val intent=Intent(Intent.ACTION_PICK)
        intent.data=MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        intent.type="image/*"
        resultLauncher_g.launch(intent)
    }
    private fun saveImageFile(filename: String, mimeType: String, bitmap: Bitmap):Uri? {
        var values= ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME,filename)
        values.put(MediaStore.Images.Media.MIME_TYPE,mimeType)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            values.put(MediaStore.Images.Media.IS_PENDING,1)
        }
        val uri=requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
        try {
            if(uri!=null){
                val descriptor=requireContext().contentResolver.openFileDescriptor(uri,"w")
                if(descriptor!=null){
                    val fos= FileOutputStream(descriptor.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos)
                    fos.close()
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
                        values.clear()
                        values.put(MediaStore.Images.Media.IS_PENDING,0)
                        requireContext().contentResolver.update(uri,values,null,null)
                    }
                }
            }
        } catch (e: java.lang.Exception){
            Log.e("File","error=")
        }
        return uri
    }
    private fun getBitmapFromView(view: ImageView): Bitmap? {
        if(view.drawable is BitmapDrawable) {
            val bitmap = (view.drawable as BitmapDrawable).bitmap
            return bitmap
        }
        return null
    }
    private fun getExtension(fileStr: String): String {
        val fileExtension = fileStr.substring(fileStr.lastIndexOf(".")+1,fileStr.length);
        return fileExtension
    }
    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                val photoFile: File? =try {
                    createImageFile()
                }catch (ex: IOException){
                    null
                }
                photoFile?.also {
                    val photoURI: Uri= FileProvider.getUriForFile(
                        this.requireContext(),
                        "com.example.tem.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI)
                    resultLauncher.launch(takePictureIntent)
                }
            }
        }
    }
    private fun createImageFile(): File? {
        val timestamp:String= SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDIR:File? =requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        //Log.d("ㄱ",File.createTempFile("JPEG_${timestamp}_",".jpg",storageDIR).absolutePath)
        return File.createTempFile("JPEG_${timestamp}_",".jpg",storageDIR)
            .apply { prefs.setImgAddress(absolutePath) }
    }
    private fun requestPermission(){
        val permission= object : PermissionListener {
            override fun onPermissionGranted() {
                //Toast.makeText(this@PluscarActivity,"카메라 시작",Toast.LENGTH_SHORT).show()
                takePhoto()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(requireContext(), "카메라 권한이 거부 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        TedPermission.create()
            .setPermissionListener(permission)
            .setDeniedMessage("카메라 권한을 허용해 주세요.")
            .setPermissions(android.Manifest.permission.CAMERA)
            .check()
    }
    private fun visible_cycle(){
        binding.txtCy.visibility=View.VISIBLE
        binding.spinReg.visibility=View.VISIBLE
    }
    private fun invisible_cycle(){
        binding.txtCy.visibility=View.INVISIBLE
        binding.spinReg.visibility=View.INVISIBLE
    }


    private fun startAlarm(c: Calendar, name: String, repeat: Int) {
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlertReceiver::class.java)
        intent.putExtra("name", name)
        intent.putExtra("repeat", repeat)
        Log.d("repeat", repeat.toString())

        // DB에 requestCode 저장하고 불러오기
        val pendingIntent =
            PendingIntent.getBroadcast(requireContext(), 1, intent, PendingIntent.FLAG_IMMUTABLE)
        if (repeat != 0) {
//            val repeatInterval = 1000L * repeat
            val repeatInterval = AlarmManager.INTERVAL_DAY * repeat
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                startCalendar!!.timeInMillis, repeatInterval,
                pendingIntent
            )
        } else {

//             설정한 시간이 현재 시간 전이라면 날짜에 +1
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1)
            }

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val selectedDate = String.format("%04d-%02d-%02d", p1, p2 + 1, p3)

        // 호출한 Fragment의 ItemBuy TextView를 찾아서 텍스트를 설정합니다.
//        val parentFragment = parentFragment
//        if (parentFragment is Fragment) {
//            var itemBuyTextView : TextView?
//            if(customArgument ==1){
//                itemBuyTextView = parentFragment.view?.findViewById<TextView>(R.id.item_buy)
//            }
//            else{
//                itemBuyTextView = parentFragment.view?.findViewById<TextView>(R.id.item_date)
//            }
//            itemBuyTextView?.text = selectedDate
//        }
        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, p1)
        c.set(Calendar.MONTH, p2)
        c.set(Calendar.DATE, p3)
        c.set(Calendar.HOUR_OF_DAY, 10)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND,0)
        if (customArgument == 1) {
            binding.itemBuy.text = selectedDate
            startCalendar = c
        } else {
            binding.itemDate.text = selectedDate
            endCalendar = c
        }
    }


    // 알람취소하기
    private fun cancelAlarm(requestId: Int) {
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), requestId, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)
    }

}