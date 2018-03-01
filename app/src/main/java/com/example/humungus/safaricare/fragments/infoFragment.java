package com.example.humungus.safaricare.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.humungus.safaricare.R;
import com.example.humungus.safaricare.activities.LoginActivitry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class infoFragment extends Fragment {
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private static final int CHOOSE_IMAGE = 123;
    Button changeImgBtn, LogOutBtn;
    ImageButton saveButton;
    EditText UsernameET;
    ImageView DpIV;
    TextView useremailTV;
    Bitmap bitmap;
    String profileImageUri;

    Uri uriProfileImage;
    ProgressBar progressBar;

    public infoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info,container,false);

        changeImgBtn = (Button) view.findViewById(R.id.edit_imagebtn);
        saveButton = (ImageButton) view.findViewById(R.id.editnamebtn);
        LogOutBtn = (Button) view.findViewById(R.id.logoutbtn);
        UsernameET = (EditText) view.findViewById(R.id.username);
        DpIV = (ImageView) view.findViewById(R.id.profile_image);
        useremailTV = (TextView)view.findViewById(R.id.useremail);

        progressBar = (ProgressBar)view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();

        changeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
            }
        });

        LogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(new Intent(getContext(), LoginActivitry.class));
            }
        });

        loadUserInformation();
        return view;
    }

    private void loadUserInformation() {
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            if(currentUser.getPhotoUrl() !=null){
                Glide.with(this)
                        .load(currentUser.getPhotoUrl().toString())
                        .into(DpIV);
            }

            if(currentUser.getDisplayName() != null){
                UsernameET.setText(currentUser.getDisplayName());
            }

            useremailTV.setText("Email:"+currentUser.getEmail());
            progressBar.setVisibility(View.GONE);

        }
    }

    private void saveUserInformation() {
        String displayName = UsernameET.getText().toString();

        if(displayName.isEmpty()){
            UsernameET.setError("Username Required");
            UsernameET.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null && profileImageUri != null){
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUri))
                    .build();
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), CHOOSE_IMAGE);
    }

    public void uploadImageToFireBAse(){

        mStorageRef = FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis() + ".jpg");

        if(uriProfileImage != null) {
            progressBar.setVisibility(View.VISIBLE);
            mStorageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            // Get a URL to the uploaded content
                            profileImageUri = taskSnapshot.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressBar.setVisibility(View.GONE);
                            // Handle unsuccessful uploads
                            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriProfileImage = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uriProfileImage);
                DpIV.setImageBitmap(bitmap);
                uploadImageToFireBAse();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
